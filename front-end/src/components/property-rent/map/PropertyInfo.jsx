import React, {PureComponent} from 'react';
import {Link} from "react-router-dom";

export default class PropertyInfo extends PureComponent {

	render() {
		const {property} = this.props;

		return (
			<div>
				<Link className="text-black" to={`/properties/${property.id}`}>
					<div className="col">
						<img width={240} className="img-fluid mt-3 rounded d-flex" src={this.props.imageLink}/>
						<div>
							<div className="d-flex">{property.title}</div>
						</div>
					</div>

				</Link>
			</div>
		);
	}
}