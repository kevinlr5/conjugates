import * as React from 'react';
import entityApi from '../api/entity';

interface Props {
    params: any;
}

interface State {
    entity: EntityResponse | null;
}

export default class Entity extends React.Component<Props, State> {

    constructor(props: Props, context: any) {
        super(props, context);
        this.state = {
            entity: null,
        };
    }

    public componentDidMount() {
        this.loadEntity();
    }

    public render() {
        return <div>
                <h4>Entity</h4>
                {this.renderBody()}
            </div>;
    }

    private renderBody() {
        if (this.state.entity) {
            return <div>
                <h5 className='subheader'>Summary</h5>
                {this.renderSummary(this.state.entity)}
                <h5 className='subheader'>Article References</h5>
                {this.renderArticles(this.state.entity.articles)}
            </div>;
        } else {
            return this.renderLoading();
        }
    }

    private renderSummary(response: EntityResponse) {
        return <div>
            <ul className='square indented-section'>
                <li>Value:  {response.entity.rawValue}</li>
                <li>Type: {response.entity.type}</li>
                <li>Average Score: {response.averageScore}</li>
                <li>Number of Mentions: {response.numberOfMentions}</li>
            </ul>
        </div>;
    }

    private renderArticles(articles: ArticleReference[]) {
        return <ul className='square indented-section'>
                {this.renderArticleReferences(articles)}
            </ul>;
    }

    private renderArticleReferences(articles: ArticleReference[]) {
        return articles.map(this.renderReferenceAsEntry);
    }

    private renderReferenceAsEntry(article: ArticleReference) {
        return <li key={article.id}>
            Article: {article.title}, Score: {article.entityScore.averageScore}
        </li>;
    }

    private renderLoading() {
        return <span className='indented-section'>
                    <i className='fa fa-spinner fa-spin fa-3x fa-fw article-loading-spinner' />
                    <span>Loading entity info...</span>
                </span>;
    }

    private loadEntity() {
        entityApi.getEntity(this.props.params.value).then(result => {
            this.setState({
                entity: result,
            });
        });
    }

}
