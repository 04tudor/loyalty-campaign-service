export class SuggestionDto {
    constructor({
                    id,
                    title,
                    content,
                    createdAt,
                    authorUsername,
                    authorName,
                    authorAvatar,
                    ownSuggestion,
                    likeCount,
                    dislikeCount,
                    currentUserVote
                }) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt ? new Date(createdAt) : null;
        this.authorUsername = authorUsername;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.ownSuggestion = ownSuggestion ?? false;
        this.likeCount = likeCount ?? 0;
        this.dislikeCount = dislikeCount ?? 0;
        this.currentUserVote = currentUserVote ?? null;
    }

    get hasLiked() {
        return this.currentUserVote === 'LIKE';
    }

    get hasDisliked() {
        return this.currentUserVote === 'DISLIKE';
    }

    static fromApiResponse(data) {
        return new SuggestionDto(data);
    }
}