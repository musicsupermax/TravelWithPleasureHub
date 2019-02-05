import React, {Component} from 'react';

class ErrorBoundary extends Component {
	constructor(props) {
		super(props);
		this.state = {
			hasError: false,
			error: null
		};
	}


	componentDidCatch(error, info) {
		this.setState({
			hasError: true,
			error: error
		});
	}

	render() {
		if (this.state.hasError) {
			return (
				<div className="container text-center">
					<h1 className="text-black">Oops! Error!</h1>
					<div>
						{this.state.error instanceof String && this.state.error}
					</div>
				</div>
			);
		}
		return this.props.children;
	}
}

export default ErrorBoundary;