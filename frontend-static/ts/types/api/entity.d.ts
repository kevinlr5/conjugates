interface Entity {
    value: string;
    rawValue: string;
    type: string;
}

interface EntityScore {
    entity: Entity;
    averageScore: number;
    aggregateScore: number;
    numberOfMentions: number;
    weight: number;
}

interface ArticleReference {
    id: number;
    articleId: number;
    title: number;
    entityScore: EntityScore;
}

interface EntityResponse {
    entity: Entity;
    averageScore: number;
    numberOfMentions: number;
    articles: ArticleReference[];
}

interface EntityApi {
	getEntity(value: string): Promise<EntityResponse>;
}