import * as React from 'react';

interface Props {
    analyzeArticleResponse: AnalyzeArticleResponse;
    handleClear: () => void;
}

export default class AnalyzedArticle extends React.Component<Props, {}> {

    constructor(props: Props, context: any) {
        super(props, context);
        this.handleClear = this.handleClear.bind(this);
    }

    public render() {
        return <div className='analyzed-article'>
                <h4>Results</h4>
                {this.renderKey()}
                {this.renderDocumentScore('Title', this.props.analyzeArticleResponse.titleScore)}
                {this.renderDocumentScore('Body', this.props.analyzeArticleResponse.bodyScore)}
                <button onClick={this.handleClear}>Clear</button>
            </div>;
    }

    private renderKey() {
        return <span>
                <h5 className='subheader'>Key:</h5>
                <ul className='square indented-section'>
                    <li>Score: 100 - very positive; 50 - neutral; 0 - very negative</li>
                    <li>Weight: How much analysis was done</li>
                    <li>Entities: Table of individual entities in article</li>
                </ul>
            </span>;
    }

    private renderDocumentScore(header: string, documentScore: DocumentScore) {
        return <div>
                <h5 className='subheader'>{header}:</h5>
                <ul className='square indented-section'>
                    <li>Score: {documentScore.averageScore}</li>
                    <li>Weight: {documentScore.weight}</li>
                    <li>Entities: {this.renderEntityTable(documentScore)}</li>
                </ul>
            </div>;
    }

    private renderEntityTable(documentScore: DocumentScore) {
        if (documentScore.entityScores.length !== 0) {
            return <table>
                    <thead>
                        <tr>
                            <th>Entity</th>
                            <th>Type</th>
                            <th>Score</th>
                            <th>Weight</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderEntityRows(documentScore.entityScores)}
                    </tbody>
                </table>;
        } else {
            return 'None found';
        }
    }

    private renderEntityRows(scores: EntityScore[]) {
        return scores.slice()
            .sort(this.compareByWeight)
            .map(this.renderEntityAsRow);
    }

    private renderEntityAsRow(entity: EntityScore) {
        return <tr key={entity.entity.value}>
                <td>{entity.entity.value}</td>
                <td>{entity.entity.type}</td>
                <td>{entity.averageScore}</td>
                <td>{entity.weight}</td>
            </tr>;
    }

    private compareByWeight(one: EntityScore, two: EntityScore) {
        return two.weight - one.weight;
    }

    private handleClear() {
        this.props.handleClear();
    }

}
