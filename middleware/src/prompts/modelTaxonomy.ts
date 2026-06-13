// Compressed model taxonomy injected into the AI system prompt.
// Contains id, name, domain, shortDesc, and tags for all 250+ models.
// Full data lives in models.json — this is the lightweight prompt version.

export const modelTaxonomy = `
[DECISION MAKING]
first-principles-thinking | First Principles Thinking | Break problems to fundamental truths; rebuild from ground up | reasoning,problem-solving,innovation
second-order-thinking | Second-Order Thinking | Consider consequences of consequences, not just immediate effects | consequences,strategy,planning
inversion | Inversion | Solve by thinking about what to avoid, then don't do it | problem-solving,strategy,risk
regret-minimisation-framework | Regret Minimisation Framework | Project to age 80; choose the path you'd regret not taking | decisions,long-term,risk
expected-value | Expected Value | Probability × magnitude; compare weighted outcomes | probability,risk,decisions
premortem-analysis | Premortem Analysis | Imagine failure has occurred; work backwards to prevent it | risk,planning,teams
reversible-vs-irreversible-decisions | Reversible vs Irreversible Decisions | Move fast on reversible; slow and careful on irreversible | decisions,risk,speed
the-10-10-10-rule | 10/10/10 Rule | How will you feel about this decision in 10 mins, 10 months, 10 years? | perspective,emotions,decisions
the-eisenhower-matrix | Eisenhower Matrix | Sort tasks by urgent/important into four quadrants | productivity,prioritisation,time
bayesian-reasoning | Bayesian Reasoning | Update beliefs when new evidence arrives; prior × likelihood | probability,beliefs,evidence
the-ooda-loop | OODA Loop | Observe, Orient, Decide, Act — iterate faster than the opposition | strategy,speed,military
margin-of-safety | Margin of Safety | Build in buffer between expected and maximum load | risk,investing,engineering
the-cynefin-framework | Cynefin Framework | Map situations to: simple, complicated, complex, or chaotic | complexity,leadership,decisions
the-80-20-rule | 80/20 Rule (Pareto Principle) | 80% of results come from 20% of causes | productivity,prioritisation,leverage
chestertons-fence | Chesterton's Fence | Understand why something exists before removing it | change,decisions,systems
the-5-whys | 5 Whys | Ask why five times to reach root cause | root-cause,problem-solving,Toyota
minimum-viable-decision | Minimum Viable Decision | Make the smallest decision that lets you learn and iterate | lean,uncertainty,speed
satisficing-vs-maximising | Satisficing vs Maximising | Good enough often beats exhaustive search for the perfect option | decisions,psychology,Herbert-Simon

[COGNITIVE RAZORS]
occams-razor | Occam's Razor | Prefer the simplest explanation that fits the evidence | simplicity,logic,science
hanlons-razor | Hanlon's Razor | Never attribute to malice what can be explained by incompetence | relationships,conflict,bias
hitchens-razor | Hitchens's Razor | What can be asserted without evidence can be dismissed without evidence | logic,epistemology,debate
goodharts-law | Goodhart's Law | When a measure becomes a target, it ceases to be a good measure | metrics,incentives,management
brandolinis-law | Brandolini's Law | The energy to refute nonsense is far greater than to produce it | communication,misinformation,debate
humes-guillotine | Hume's Guillotine | You cannot derive 'ought' from 'is' — facts don't imply values | ethics,logic,philosophy
popper-falsifiability | Popper's Falsifiability | A claim is scientific only if it can in principle be proven false | science,epistemology,testing
cunninghams-law | Cunningham's Law | The best way to get the right answer is to post the wrong one | communication,communities,knowledge
the-peter-principle | Peter Principle | People rise to their level of incompetence | management,organisations,careers

[SYSTEMS THINKING]
feedback-loops | Feedback Loops | Outputs that become inputs, either amplifying or stabilising the system | systems,cycles,complexity
leverage-points | Leverage Points | Places in a system where a small shift produces big changes | systems,change,strategy
the-iceberg-model | Iceberg Model | Visible events are driven by hidden patterns, structures, and mental models | systems,root-cause,analysis
emergence | Emergence | Complex behaviour arising from simple rules — the whole is more than its parts | complexity,organisation,biology
non-linearity-and-tipping-points | Tipping Points | Small changes build until a threshold triggers rapid system change | systems,change,complexity
the-cobra-effect | Cobra Effect (Perverse Incentives) | Incentives designed to fix problems sometimes make them worse | incentives,policy,management
the-flywheel-effect | Flywheel Effect | Consistent effort compounds into unstoppable momentum | strategy,growth,persistence
the-map-is-not-the-territory | Map Is Not the Territory | Our models of reality are not reality itself — beware mistaking them | epistemology,systems,thinking
path-dependency | Path Dependency | Past choices constrain future options even when better paths exist | history,strategy,systems
complex-adaptive-systems | Complex Adaptive Systems | Systems of agents that adapt and self-organise in response to environment | complexity,emergence,biology

[COGNITIVE BIASES]
availability-heuristic | Availability Heuristic | Judge probability by how easily examples come to mind | memory,judgment,risk
confirmation-bias | Confirmation Bias | Seek, favour, and recall information that confirms existing beliefs | beliefs,reasoning,research
survivorship-bias | Survivorship Bias | Focus on successes while ignoring unseen failures | data,history,investing
sunk-cost-fallacy | Sunk Cost Fallacy | Continue investing in something because of past investment, not future value | decisions,economics,psychology
loss-aversion | Loss Aversion | Losses feel roughly twice as painful as equivalent gains feel good | decisions,risk,behavioural-economics
anchoring-bias | Anchoring Bias | Over-rely on the first piece of information encountered | negotiation,judgment,pricing
dunning-kruger-effect | Dunning-Kruger Effect | Low-ability individuals overestimate their competence | self-awareness,learning,expertise
status-quo-bias | Status Quo Bias | Prefer the current state of affairs even when change is beneficial | decisions,change,psychology
planning-fallacy | Planning Fallacy | Underestimate time, costs, and risks of future actions | projects,planning,time
optimism-bias | Optimism Bias | Overestimate the likelihood of positive events happening to us | risk,judgment,psychology
framing-effect | Framing Effect | The way information is presented changes the decision made | communication,decisions,language
narrative-fallacy | Narrative Fallacy | Impose a coherent story on random events to feel they make sense | logic,history,risk
the-halo-effect | Halo Effect | One positive trait causes us to assume other positive traits | judgment,people,bias
the-ikea-effect | IKEA Effect | We value things more when we help create them | product,management,psychology
recency-bias | Recency Bias | Give undue weight to recent events when predicting the future | investing,judgment,memory
blind-spot-bias | Blind Spot Bias | Recognise bias in others but not in ourselves | self-awareness,bias,psychology

[PHILOSOPHY & ETHICS]
the-categorical-imperative | Categorical Imperative | Act only according to rules you'd will to become universal law | ethics,Kant,decision-making
utilitarianism | Utilitarianism | Maximise overall well-being; the greatest good for the greatest number | ethics,decisions,policy
the-veil-of-ignorance | Veil of Ignorance | Design systems as if you didn't know your place in them | ethics,justice,fairness
virtue-ethics | Virtue Ethics | Focus on character and becoming a good person, not just good acts | ethics,character,Aristotle
the-dichotomy-of-control | Dichotomy of Control | Distinguish what is in your control from what isn't; focus on the former | stoicism,mindset,anxiety
memento-mori | Memento Mori | Remember you will die; let mortality clarify what matters | stoicism,priorities,perspective
amor-fati | Amor Fati | Love your fate — embrace everything that happens as necessary | stoicism,resilience,acceptance
negative-visualisation | Negative Visualisation | Imagine losing what you value to appreciate it and prepare for loss | stoicism,resilience,gratitude
existentialism | Existentialism | We are radically free; we create our own meaning through choices | philosophy,freedom,responsibility
epistemic-humility | Epistemic Humility | Acknowledge the limits of your own knowledge | knowledge,intellectual,Socrates

[STRATEGY & BUSINESS]
porters-five-forces | Porter's Five Forces | Analyse industry attractiveness via rivalry, suppliers, buyers, substitutes, entrants | strategy,competitive,business
blue-ocean-strategy | Blue Ocean Strategy | Create uncontested market space rather than competing in existing markets | strategy,innovation,competition
the-innovators-dilemma | Innovator's Dilemma | Successful companies can fail by serving existing customers too well | strategy,innovation,disruption
network-effects | Network Effects | A product becomes more valuable as more people use it | growth,strategy,platforms
competitive-moats | Competitive Moats | Durable competitive advantages that protect long-term profitability | investing,strategy,business
jobs-to-be-done | Jobs-to-be-Done | Customers hire products to do a job; understand the job, not the product | product,strategy,innovation
batna | BATNA | Know your Best Alternative To a Negotiated Agreement | negotiation,decisions,power
okrs | OKRs | Objectives and Key Results — align ambition with measurable outcomes | management,strategy,goal-setting
theory-of-constraints | Theory of Constraints | Every system has one binding constraint; focus improvement there | operations,management,optimisation
the-ansoff-matrix | Ansoff Matrix | Four growth strategies: market penetration, development, product dev, diversification | strategy,growth,business

[SCIENTIFIC THINKING]
base-rate-fallacy | Base Rate Fallacy | Ignore statistical base rates in favour of specific case information | probability,statistics,judgment
correlation-vs-causation | Correlation vs Causation | Two things moving together does not mean one causes the other | statistics,logic,research
black-swan-theory | Black Swan Theory | Rare, high-impact events that are unpredictable but explicable in hindsight | risk,uncertainty,probability
antifragility | Antifragility | Some things benefit from shocks and volatility — build systems that do | resilience,risk,complexity
fermi-estimation | Fermi Estimation | Break down unknown quantities into estimable components | problem-solving,estimation,reasoning
regression-to-the-mean | Regression to the Mean | Extreme events tend to be followed by more average ones | statistics,investing,performance
nash-equilibrium | Nash Equilibrium | A stable state where no player benefits from changing strategy unilaterally | game-theory,strategy,economics
the-prisoners-dilemma | Prisoner's Dilemma | Two rational actors may fail to cooperate even when cooperation is optimal | game-theory,cooperation,strategy
kuhn-paradigm-shifts | Paradigm Shifts | Science advances through anomalies that trigger revolutionary framework changes | science,change,epistemology
the-scientific-method | Scientific Method | Hypothesise, test, measure, revise — the engine of reliable knowledge | science,reasoning,truth

[PSYCHOLOGY & BEHAVIOUR]
maslows-hierarchy-of-needs | Maslow's Hierarchy of Needs | Physiological→Safety→Love→Esteem→Self-actualisation — lower needs first | motivation,psychology,leadership
growth-mindset-vs-fixed-mindset | Growth vs Fixed Mindset | Growth: abilities develop through effort. Fixed: abilities are innate | learning,mindset,performance
cialdinis-six-principles | Cialdini's Six Principles | Reciprocity, Commitment, Social Proof, Authority, Liking, Scarcity | persuasion,influence,marketing
flow-state | Flow State | Deep concentration and effortless engagement when challenge meets skill | productivity,creativity,performance
cognitive-dissonance | Cognitive Dissonance | Discomfort from holding contradictory beliefs; we resolve it by rationalising | psychology,beliefs,change
learned-helplessness | Learned Helplessness | After repeated failure, we stop trying even when success is possible | psychology,resilience,depression
the-spotlight-effect | Spotlight Effect | We overestimate how much others notice our appearance and mistakes | social,self-awareness,anxiety
locus-of-control | Locus of Control | Internal: you control outcomes. External: outcomes are controlled by fate | mindset,agency,psychology

[ECONOMICS & FINANCE]
opportunity-cost | Opportunity Cost | Every choice has a cost — the value of the best alternative foregone | decisions,economics,resource-allocation
comparative-advantage | Comparative Advantage | Trade based on relative, not absolute, efficiency — gains from specialisation | economics,trade,strategy
nudge-theory | Nudge Theory | Shape choices by changing default options and the choice architecture | behaviour,policy,design
prospect-theory | Prospect Theory | People value gains and losses differently; losses loom larger | behaviour,investing,decisions
mental-accounting | Mental Accounting | We treat money differently based on its mental category or source | psychology,economics,investing
the-time-value-of-money | Time Value of Money | A dollar today is worth more than a dollar tomorrow | finance,investing,decisions

[LEADERSHIP & MANAGEMENT]
servant-leadership | Servant Leadership | Leaders exist to serve their team, removing obstacles and growing people | leadership,management,culture
psychological-safety | Psychological Safety | Teams perform best when members feel safe to speak up and take risks | teams,culture,management
radical-candor | Radical Candor | Care personally while challenging directly — kind honesty | feedback,management,culture
situational-leadership | Situational Leadership | Match leadership style to team member's development level | leadership,management,coaching
the-pyramid-principle | Pyramid Principle | Lead with the conclusion, then support with evidence in logical order | communication,writing,consulting

[CREATIVITY & INNOVATION]
design-thinking | Design Thinking | Empathise, Define, Ideate, Prototype, Test — human-centred problem solving | innovation,product,empathy
lateral-thinking | Lateral Thinking | Approach problems from unexpected angles; escape vertical logic patterns | creativity,problem-solving,thinking
the-six-thinking-hats | Six Thinking Hats | Six modes of thinking (facts, emotions, risks, benefits, creativity, process) | thinking,teams,decisions
the-build-measure-learn-loop | Build-Measure-Learn Loop | Ship fast, measure outcomes, learn and iterate | startups,product,lean

[COMMUNICATION & RHETORIC]
aristotles-rhetorical-triangle | Aristotle's Rhetorical Triangle | Ethos (credibility), Pathos (emotion), Logos (logic) — the three pillars of persuasion | persuasion,communication,rhetoric
steelmanning | Steelmanning | Argue against the strongest version of your opponent's position | logic,debate,intellectual-honesty
the-overton-window | Overton Window | The range of politically acceptable ideas shifts over time | politics,communication,change
the-heros-journey | Hero's Journey | Transformation narrative: ordinary world → challenge → ordeal → return | storytelling,narrative,change

[LEARNING & KNOWLEDGE]
the-feynman-technique | Feynman Technique | Explain a concept simply; identify gaps when you can't | learning,teaching,understanding
spaced-repetition | Spaced Repetition | Review information at increasing intervals to lock it in long-term memory | memory,learning,retention
the-curse-of-knowledge | Curse of Knowledge | Experts struggle to explain concepts because they can't imagine not knowing them | communication,teaching,expertise
double-loop-learning | Double-Loop Learning | Don't just fix mistakes — question the assumptions that caused them | learning,organisations,improvement
the-lindy-effect | Lindy Effect | Non-perishable things that have survived long are likely to survive longer | investing,ideas,longevity

[NATURE & EVOLUTION]
natural-selection | Natural Selection | Traits that improve survival and reproduction spread through populations | evolution,adaptation,competition
the-red-queen-effect | Red Queen Effect | You must keep running just to stay in the same place | competition,evolution,strategy
punctuated-equilibrium | Punctuated Equilibrium | Evolution proceeds in bursts separated by long stable periods | change,strategy,disruption

[CHANGE & TRANSFORMATION]
kotters-8-step-change-model | Kotter's 8-Step Change Model | Create urgency → coalition → vision → communicate → empower → wins → consolidate → anchor | change,leadership,organisations
the-diffusion-of-innovations | Diffusion of Innovations | Innovations spread through Innovators→Early Adopters→Majority→Laggards | strategy,growth,marketing
the-kubler-ross-change-curve | Kübler-Ross Change Curve | People move through: denial → anger → bargaining → depression → acceptance | change,emotions,leadership
identity-based-habits | Identity-Based Habits | Change behaviour by changing self-image first — be, then do | behaviour,change,habits
the-aggregation-of-marginal-gains | Aggregation of Marginal Gains | 1% improvement in every area compounds to dramatic overall progress | improvement,performance,strategy

[GEOPOLITICS & SOCIOLOGY]
the-thucydides-trap | Thucydides Trap | When a rising power threatens an established one, war often follows | geopolitics,history,power
moral-panics | Moral Panics | Media-amplified fear about perceived threats to social values | sociology,media,society
social-capital | Social Capital | The value generated by social networks and relationships | sociology,networks,trust
`.trim();
