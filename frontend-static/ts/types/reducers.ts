type ActionHandler<S, A extends Action> = (state: S, action: A) => Partial<S>;

interface ActionHandlers<S> {
	ASYNC_REQUEST?: ActionHandler<S, AsyncRequestAction>;
	ASYNC_RESPONSE?: ActionHandler<S, AsyncResponseAction>;
	SET_ARTICLE_ANALYSIS?: ActionHandler<S, SetArticleAnalysisAction>;
	CLEAR_ARTICLE?: ActionHandler<S, ClearArticleAction>;
}