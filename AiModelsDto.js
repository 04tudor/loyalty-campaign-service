export class AiModelDto {
    constructor(data = {}) {
        this.id = data.id || null;
        this.ai = data.ai || '';
        this.model = data.model || '';
        this.label = data.label || '';
    }
}