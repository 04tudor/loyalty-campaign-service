export class RepositoryConfigDto {
    constructor({ aiModelDto, reviewAspects, reviewEnabled, pushReviewEnabled, prReviewEnabled } = {}) {
        this.aiModelDto = aiModelDto || null;
        this.reviewAspects = reviewAspects || null;
        this.reviewEnabled = reviewEnabled !== undefined ? reviewEnabled : null;
        this.pushReviewEnabled = pushReviewEnabled !== undefined ? pushReviewEnabled : null;
        this.prReviewEnabled = prReviewEnabled !== undefined ? prReviewEnabled : null;
    }

    toJSON() {
        return {
            aiModelDto: this.aiModelDto,
            reviewAspects: this.reviewAspects,
            reviewEnabled: this.reviewEnabled,
            pushReviewEnabled: this.pushReviewEnabled,
            prReviewEnabled: this.prReviewEnabled
        };
    }

    static fromJSON(data) {
        return new RepositoryConfigDto({
            aiModelDto: data.aiModelDto || null,
            reviewAspects: data.reviewAspects || null,
            reviewEnabled: data.reviewEnabled !== undefined ? data.reviewEnabled : null,
            pushReviewEnabled: data.pushReviewEnabled !== undefined ? data.pushReviewEnabled : null,
            prReviewEnabled: data.prReviewEnabled !== undefined ? data.prReviewEnabled : null
        });
    }
}

export const DEFAULT_REVIEW_ASPECTS = [
    "Summary",
    "Syntax & Style",
    "Correctness & Logic",
    "Potential Bugs",
    "Security Considerations",
    "Performance & Scalability",
    "Maintainability & Readability",
    "Documentation & Comments",
    "Best Practices & Design Principles",
    "Recommendations"
];

export function createAiModel(id, ai, model) {
    return { id, ai, model };
}

export class RepositoryConfigBuilder {
    constructor() {
        this.config = {
            aiModelDto: null,
            reviewAspects: null,
            reviewEnabled: null,
            pushReviewEnabled: null,
            prReviewEnabled: null
        };
    }

    setAiModel(aiModelDto) {
        this.config.aiModelDto = aiModelDto;
        return this;
    }

    setAiModelById(id, ai, model) {
        this.config.aiModelDto = createAiModel(id, ai, model);
        return this;
    }

    setReviewAspects(reviewAspects) {
        this.config.reviewAspects = reviewAspects;
        return this;
    }

    setReviewEnabled(enabled) {
        this.config.reviewEnabled = enabled;
        return this;
    }

    setPushReviewEnabled(enabled) {
        this.config.pushReviewEnabled = enabled;
        return this;
    }

    setPrReviewEnabled(enabled) {
        this.config.prReviewEnabled = enabled;
        return this;
    }

    build() {
        return new RepositoryConfigDto(this.config);
    }
}