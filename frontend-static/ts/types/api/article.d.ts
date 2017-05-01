interface AnalyzeArticleRequest {
    title: string;
    body: string;
}

interface Entity {
    value: string;
    type: string;
}

interface EntityScore {
    entity: Entity;
    averageScore: number;
    aggregateScore: number;
    numberOfMentions: number;
    weight: number;
}

interface DocumentScore {
    entityScores: EntityScore[];
    averageScore: number;
    weight: number;
}

interface AnalyzeArticleResponse {
    titleScore: DocumentScore;
    bodyScore: DocumentScore;
}

interface ArticleApi {
	analyzeArticle(request: AnalyzeArticleRequest): Promise<AnalyzeArticleResponse>;
}