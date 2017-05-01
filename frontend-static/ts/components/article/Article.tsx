import * as React from 'react';
import ArticleForm from './ArticleForm';
import AnalyzedArticle from './AnalyzedArticle';
import {connect} from 'react-redux';
import {dispatchToPropsObject} from '../../actions';

interface StateProps {
    analyzeArticleResponse: AnalyzeArticleResponse | null;
}

type Props =  DispatchProps & StateProps;

function mapStateToProps(state: AppState): StateProps {
    return {
        analyzeArticleResponse: state.article.analyzeArticleResponse,
    };
}

class Article extends React.Component<Props, {}> {

    public render() {
        if (this.props.analyzeArticleResponse) {
            return <AnalyzedArticle
                handleClear={this.props.clearArticle}
                analyzeArticleResponse={this.props.analyzeArticleResponse}
            />;
        } else {
            return <ArticleForm handleSubmit={this.props.analyzeArticle} />;
        }
    }

}

export default connect<StateProps, DispatchProps, {}>
    (mapStateToProps, dispatchToPropsObject)(Article);
