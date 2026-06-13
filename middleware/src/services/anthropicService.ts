import Anthropic from '@anthropic-ai/sdk';
import { buildSystemPrompt, buildUserPrompt } from '../prompts/systemPrompt';
import { modelTaxonomy } from '../prompts/modelTaxonomy';

const client = new Anthropic({ apiKey: process.env.ANTHROPIC_API_KEY });

export interface AnalysisInput {
  situationText: string;
  moodContext?: string;
  urgencyContext?: string;
  deepAnalysis: boolean;
  previousModelIds: string[];
}

export interface AnalysisOutput {
  primaryModelId: string;
  primaryContextApplication: string;
  secondaryModelIds: string[];
  secondaryReasons: string[];
  blindSpotModelId: string | null;
  blindSpotText: string | null;
  conservativePath: { title: string; description: string; modelJustification: string; riskLevel: string; rewardLevel: string };
  balancedPath: { title: string; description: string; modelJustification: string; riskLevel: string; rewardLevel: string };
  boldPath: { title: string; description: string; modelJustification: string; riskLevel: string; rewardLevel: string };
  deepSynthesis?: {
    convergence: string;
    tension: string;
    recommendation: string;
    overlookedModel: string;
  } | null;
}

export async function runAnalysis(input: AnalysisInput): Promise<AnalysisOutput> {
  const systemPrompt = buildSystemPrompt(modelTaxonomy, input.deepAnalysis);
  const userPrompt = buildUserPrompt(input);

  const response = await client.messages.create({
    model: 'claude-sonnet-4-6',
    max_tokens: input.deepAnalysis ? 3000 : 1500,
    system: systemPrompt,
    messages: [{ role: 'user', content: userPrompt }]
  });

  const text = response.content[0].type === 'text' ? response.content[0].text : '';

  // Extract JSON from response (model is instructed to return only JSON)
  const jsonMatch = text.match(/\{[\s\S]*\}/);
  if (!jsonMatch) throw new Error('Invalid AI response format');

  const parsed = JSON.parse(jsonMatch[0]) as AnalysisOutput;
  validateAnalysisOutput(parsed);
  return parsed;
}

function validateAnalysisOutput(output: Partial<AnalysisOutput>): asserts output is AnalysisOutput {
  if (!output.primaryModelId) throw new Error('Missing primaryModelId in AI response');
  if (!output.primaryContextApplication) throw new Error('Missing primaryContextApplication');
  if (!output.conservativePath || !output.balancedPath || !output.boldPath) {
    throw new Error('Missing decision paths in AI response');
  }
}
