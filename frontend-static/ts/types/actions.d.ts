import * as Redux from 'redux';
import * as ReduxThunk from 'redux-thunk';
import * as ReactRedux from 'react-redux';

declare global {

	interface AsyncRequestAction extends Redux.Action {
		type: 'ASYNC_REQUEST';
		requestId: number;
	}

	interface AsyncResponseAction extends Redux.Action {
		type: 'ASYNC_RESPONSE';
		requestId: number;
		error: Error | null;
	}

	interface SetArticleAnalysisAction extends Redux.Action {
		type: 'SET_ARTICLE_ANALYSIS';
		analyzeArticleResponse: AnalyzeArticleResponse;
	}

	interface ClearArticleAction extends Redux.Action {
		type: 'CLEAR_ARTICLE';
	}
	
	type Action = AsyncRequestAction | 
		AsyncResponseAction |
		SetArticleAnalysisAction |
		ClearArticleAction;

	type ThunkAction<R> = ReduxThunk.ThunkAction<R, AppState, void>;
	type ActionCreator<P> = (params: P) => Action;
	type AsyncAction<R> = ThunkAction<Promise<R>>;
	type AsyncActionCreator<P, R> = (params: P) => AsyncAction<R>;


	interface DispatchProps {
		analyzeArticle: ArticleApi['analyzeArticle'];
		clearArticle: () => void;
	}

	interface DispatchToPropsObject extends ReactRedux.MapDispatchToPropsObject {
		analyzeArticle: AsyncActionCreator<AnalyzeArticleRequest, AnalyzeArticleResponse>;
		clearArticle: ActionCreator<void>;
	}

}