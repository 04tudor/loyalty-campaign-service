export const FeedbackStatus = {
    PROCESSING: 'PROCESSING',
    COMPLETED: 'COMPLETED',
    FAILED: 'FAILED'
};

export const FeedbackType = {
    PR_REVIEW: 'PR_REVIEW',
    PUSH_REVIEW: 'PUSH_REVIEW',
    PROJECT_ANALYSIS: 'PROJECT_ANALYSIS',
    PROJECT_AUDIT: 'PROJECT_AUDIT'
};

export class RepositoryDetailsDto {
    constructor(data = {}) {
        this.id = data.id || null;
        this.fullName = data.fullName || 'Unknown Repository';
        this.name = data.name || '';
        this.permission = data.permission || '';
        this.reviewEnabled = data.reviewEnabled || false;
        this.lastSyncedAt = data.lastSyncedAt ? new Date(data.lastSyncedAt) : null;
    }
}

export class FeedbackDto {
    constructor(data = {}) {
        this.id = data.id || null;
        this.repository = data.repository ? new RepositoryDetailsDto(data.repository) : null;
        this.prId = data.prId || null;
        this.commitHash = data.commitHash || null;
        this.comment = data.comment || '';
        this.model = data.model || '';
        this.createdAt = data.createdAt ? new Date(data.createdAt) : new Date();
        this.prAuthorUuid = data.prAuthorUuid || '';
        this.prAuthorUsername = data.prAuthorUsername || 'Unknown';
        this.reviewedByUsername = data.reviewedByUsername || '';
        this.rate = data.rate || 0;
        this.cost = data.cost || 0;
        this.status = data.status || FeedbackStatus.PROCESSING;
        this.feedbackType = data.feedbackType || FeedbackType.PR_REVIEW;
    }

    isProjectAnalysis() {
        return this.feedbackType === FeedbackType.PROJECT_ANALYSIS;
    }

    isProjectAudit() {
        return this.feedbackType === FeedbackType.PROJECT_AUDIT;
    }

    isPush() {
        return this.feedbackType === FeedbackType.PUSH_REVIEW || (this.prId == null && !this.isProjectAnalysis() && !this.isProjectAudit());
    }

    isPullRequest() {
        return this.feedbackType === FeedbackType.PR_REVIEW || this.prId != null;
    }

    getShortHash() {
        return this.commitHash ? this.commitHash.substring(0, 7) : 'unknown';
    }

    getFormattedDate(locale = 'ro-RO') {
        return this.createdAt.toLocaleDateString(locale);
    }
    getAiModel() {
        return this.model || 'Unknown Model';
    }

    getFormattedTime(locale = 'ro-RO') {
        return this.createdAt.toLocaleTimeString(locale, { hour: '2-digit', minute: '2-digit' });
    }

    getFormattedDateTime(locale = 'ro-RO') {
        return this.createdAt.toLocaleString(locale);
    }

    getRepositoryName() {
        return this.repository?.fullName || 'Unknown Repository';
    }

    isFailed() {
        return this.status === FeedbackStatus.FAILED;
    }

    getDisplayTitle() {
        if (this.isProjectAudit()) {
            return `Project Audit - ${this.getRepositoryName()}`;
        }
        if (this.isProjectAnalysis()) {
            return `Project Analysis - ${this.getRepositoryName()}`;
        }
        if (this.isPush()) {
            return `Push ${this.getShortHash()} by @${this.prAuthorUsername}`;
        }
        return `PR #${this.prId} by @${this.prAuthorUsername}`;
    }

    getDetailLine(locale = 'ro-RO') {
        if (this.isProjectAudit()) {
            return `Project Audit · ${this.getRepositoryName()} · ${this.getFormattedDateTime(locale)}`;
        }
        if (this.isProjectAnalysis()) {
            return `Project Analysis · ${this.getRepositoryName()} · ${this.getFormattedDateTime(locale)}`;
        }
        if (this.isPush()) {
            return `Push by @${this.prAuthorUsername} · Commit ${this.getShortHash()} · ${this.getFormattedDateTime(locale)}`;
        }
        return `PR #${this.prId} by @${this.prAuthorUsername} · ${this.getFormattedDateTime(locale)}   · ${this.getAiModel()}` ;
    }

    static fromApiResponse(data) {
        return new FeedbackDto(data);
    }

    static fromApiResponseArray(dataArray) {
        if (!Array.isArray(dataArray)) {
            return [];
        }
        return dataArray.map(data => FeedbackDto.fromApiResponse(data));
    }

    static removeDuplicates(feedbacks) {
        const uniqueMap = new Map();
        feedbacks.forEach(fb => {
            if (fb.id && !uniqueMap.has(fb.id)) {
                uniqueMap.set(fb.id, fb);
            }
        });
        return Array.from(uniqueMap.values());
    }

    static sortByDate(feedbacks, ascending = false) {
        return [...feedbacks].sort((a, b) => {
            const timeA = a.createdAt.getTime();
            const timeB = b.createdAt.getTime();
            return ascending ? timeA - timeB : timeB - timeA;
        });
    }

    static groupByRepository(feedbacks) {
        const grouped = feedbacks.reduce((acc, fb) => {
            const repoKey = fb.getRepositoryName();
            if (!acc[repoKey]) {
                acc[repoKey] = [];
            }
            acc[repoKey].push(fb);
            return acc;
        }, {});

        Object.keys(grouped).forEach(repo => {
            grouped[repo] = FeedbackDto.sortByDate(grouped[repo]);
        });

        return Object.fromEntries(
            Object.entries(grouped).sort(([, a], [, b]) => {
                const aTime = a[0]?.createdAt.getTime() || 0;
                const bTime = b[0]?.createdAt.getTime() || 0;
                return bTime - aTime;
            })
        );
    }

    static getUniqueRepositories(feedbacks) {
        const repos = new Set(feedbacks.map(fb => fb.getRepositoryName()));
        return Array.from(repos).sort();
    }

    static getUniqueAuthors(feedbacks) {
        const authors = new Set(
            feedbacks
                .map(fb => fb.prAuthorUsername)
                .filter(author => author && author !== 'Unknown' && author.trim() !== '')
        );
        return Array.from(authors).sort();
    }

    static getAuthorsForRepository(feedbacks, repositoryName) {
        if (repositoryName === 'all') {
            return FeedbackDto.getUniqueAuthors(feedbacks);
        }
        const repoFeedbacks = feedbacks.filter(fb => fb.getRepositoryName() === repositoryName);
        return FeedbackDto.getUniqueAuthors(repoFeedbacks);
    }
}

export default FeedbackDto;