import './home.scss';

import React from 'react';

import { Row, Col } from 'reactstrap';

export const Home = () => {
  return (
    <Row style={{ padding: '0 10rem', backgroundColor: '#eaeaea' }}>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9" className="align-content-center text-center">
        <h1 className="display-4">Bem vindo, ao Avisa Med Admin!</h1>
        <p className="lead">
          Seu sistema para <mark>gestão simplificada de ocorrências!</mark>
        </p>
      </Col>
    </Row>
  );
};

export default Home;
