import React, {Component} from 'react';
import {Link} from "react-router-dom";

class PropertyRentNavbar extends Component {
	render() {
		return (
			<div className="d-flex justify-content-center row mb-3">
				<div className="col-xs-4 mr-3 text-center">
					<Link to='/properties'>
						<button type="button" className="btn btn-dark">Search properties</button>
					</Link>
				</div>
				<div className="col-xs-4 mr-3 text-center">
					<Link to='/upload/property'>
						<button type="button" className="btn btn-dark">Publish ad of your property</button>
					</Link>
				</div>
				<div className="col-xs-4 text-center">
					<Link to='/rented-properties'>
						<button type="button" className="btn btn-dark">See rented properties</button>
					</Link>
				</div>
			</div>
		);
	}
}

export default PropertyRentNavbar;