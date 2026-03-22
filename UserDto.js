export class UserDto {
    constructor(data = {}) {
        this.id = data.id || null;
        this.username = data.username || '';
        this.name = data.name || '';
        this.email = data.email || '';
        this.avatar = data.avatar || '';
        this.enabled = data.enabled ?? true;
        this.roles = data.roles || [];
        this.preferredAiModel = data.preferredAiModel || null;
    }


    static fromApiResponse(apiData) {
        return new UserDto(apiData);
    }


    toJSON() {
        return {
            id: this.id,
            username: this.username,
            name: this.name,
            email: this.email,
            avatar: this.avatar,
            enabled: this.enabled,
            roles: this.roles,
            preferredAiModel: this.preferredAiModel
        };
    }

    getInitials() {
        if (this.name) {
            const names = this.name.trim().split(' ');
            if (names.length >= 2) {
                return (names[0][0] + names[names.length - 1][0]).toUpperCase();
            }
            return this.name[0].toUpperCase();
        }
        if (this.username) {
            return this.username[0].toUpperCase();
        }
        return '?';
    }


    hasRole(role) {
        return this.roles.includes(role);
    }


    isAdmin() {
        return this.hasRole('ADMIN') || this.hasRole('ROLE_ADMIN');
    }


    isSuperAdmin() {
        return this.hasRole('ROLE_SUPER_ADMIN') || this.hasRole('SUPER_ADMIN');
    }


    getDisplayName() {
        return this.name || this.username || 'Unknown User';
    }
}