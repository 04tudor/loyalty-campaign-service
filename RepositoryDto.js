
export class AiModel {
    constructor(data = {}) {
        this.id = data.id || null;
        this.ai = data.ai || '';
        this.model = data.model || '';
        this.label = data.label || '';
    }
}

export class RepositoryDto {
    constructor(data = {}) {
        this.id = data.id || null;
        this.fullName = data.fullName || '';
        this.uuid = data.uuid || '';
        this.name = data.name || '';
        this.permission = data.permission || '';
        this.userId = data.userId || null;
        this.aiModelDto = data.aiModelDto ? new AiModel(data.aiModelDto) : null;
        this.reviewAspects = data.reviewAspects || [];
        this.reviewEnabled = data.reviewEnabled || false;
        this.lastSyncedAt = data.lastSyncedAt ? new Date(data.lastSyncedAt) : null;
        this.lastConfigUpdatedAt = data.lastConfigUpdatedAt ? new Date(data.lastConfigUpdatedAt) : null;
    }


    getFormattedLastSync(locale = 'ro-RO') {
        if (!this.lastSyncedAt) return 'Never';
        return this.lastSyncedAt.toLocaleString(locale);
    }

    getFormattedLastConfigUpdate(locale = 'ro-RO') {
        if (!this.lastConfigUpdatedAt) return 'Never';
        return this.lastConfigUpdatedAt.toLocaleString(locale);
    }


    hasAiModel() {
        return this.aiModelDto !== null;
    }


    getAiModelLabel() {
        return this.aiModelDto?.label || 'No AI model';
    }


    isSynced() {
        return this.lastSyncedAt !== null;
    }


    static fromApiResponse(data) {
        return new RepositoryDto(data);
    }


    static fromApiResponseArray(dataArray) {
        if (!Array.isArray(dataArray)) {
            return [];
        }
        return dataArray.map(data => RepositoryDto.fromApiResponse(data));
    }


    static filterByReviewEnabled(repositories, enabled = true) {
        return repositories.filter(repo => repo.reviewEnabled === enabled);
    }


    static getRepositoriesWithAiModel(repositories) {
        return repositories.filter(repo => repo.hasAiModel());
    }


    static sortByLastSynced(repositories, ascending = false) {
        return [...repositories].sort((a, b) => {
            if (!a.lastSyncedAt && !b.lastSyncedAt) return 0;
            if (!a.lastSyncedAt) return 1;
            if (!b.lastSyncedAt) return -1;

            const timeA = a.lastSyncedAt.getTime();
            const timeB = b.lastSyncedAt.getTime();
            return ascending ? timeA - timeB : timeB - timeA;
        });
    }


    static sortByName(repositories, ascending = true) {
        return [...repositories].sort((a, b) => {
            const nameA = a.fullName.toLowerCase();
            const nameB = b.fullName.toLowerCase();
            return ascending ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
        });
    }
}

export default RepositoryDto;