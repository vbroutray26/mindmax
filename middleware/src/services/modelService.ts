import models from '../data/models.json';

export interface ModelRecord {
  id: string;
  name: string;
  domain: string;
  subdomain: string;
  origin: string;
  shortDesc: string;
  fullDesc: string;
  howToApply: string;
  whenToUse: string[];
  commonMistakes: string[];
  pairingLogic: string[];
  tags: string[];
  thinkerProfiles: string[];
  difficulty: string;
  isFree: boolean;
}

const modelMap = new Map<string, ModelRecord>(
  (models as ModelRecord[]).map(m => [m.id, m])
);

export function getModelById(id: string): ModelRecord | undefined {
  return modelMap.get(id);
}

export function getModelsByIds(ids: string[]): ModelRecord[] {
  return ids.flatMap(id => {
    const m = modelMap.get(id);
    return m ? [m] : [];
  });
}

export function getAllModels(): ModelRecord[] {
  return models as ModelRecord[];
}

export function getFreeModels(): ModelRecord[] {
  return (models as ModelRecord[]).filter(m => m.isFree);
}
