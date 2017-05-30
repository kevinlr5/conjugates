interface AnalyzeArticleRequest {
    title: string;
    body: string;
}

interface DocumentScore {
    entityScores: EntityScore[];
    averageScore: number;
    weight: number;
}

interface AnalyzeArticleResponse {
    id: number;
    title: string;
    titleScore: DocumentScore;
    bodyScore: DocumentScore;
}

interface ArticleApi {
	analyzeArticle(request: AnalyzeArticleRequest): Promise<AnalyzeArticleResponse>;
}