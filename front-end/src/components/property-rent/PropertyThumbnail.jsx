import React, {Component} from 'react';
import '../../property-thumbnail.css';
import './img.css';
import {Link} from "react-router-dom";
import emptyPhoto from '../../pics/empty-photo.jpg';

class PropertyThumbnail extends Component {
	constructor(props) {
		super(props);
		this.state = {
			imageLink: null
		};
		this.loadThumbnailImageLink = this.loadThumbnailImageLink.bind(this);
	}

	loadThumbnailImageLink = () => {
		fetch('http://localhost:8080/api/property-image/property/first/' + this.props.id)
			.then(response => {
				const contentType = response.headers.get("content-type");
				if (contentType && contentType.indexOf("application/json") !== -1) {
					return response.json()
						.then(responseJSON => {
							this.setState({imageLink: responseJSON.imageLink})
						})
				}
			})
			.catch(error => { throw error } )
	};


	componentWillMount() {
		this.loadThumbnailImageLink();
	}


	render() {
		return (
			<Link to={`/properties/` + this.props.id}>
				<div className="thumbnail-container" id="thumbnail-container">
					<div className="thumbnail-info rounded p-2 text-black" id="thumbnail-info">
						<div>{this.props.title}, ${this.props.price}</div>
					</div>
					{
						this.state.imageLink !== null ?
							<img className="property-image mt-3 rounded" src={this.state.imageLink}/>
							:
							<img className="property-image mt-3 rounded" src={emptyPhoto}/>
					}
				</div>
			</Link>

		);
	}
}

export default PropertyThumbnail;