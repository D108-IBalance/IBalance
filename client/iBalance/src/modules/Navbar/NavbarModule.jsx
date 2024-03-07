/* eslint-disable */

// 외부 모듈
import { useNavigate } from "react-router-dom"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

const NavbarModule = ()=>{
    let navigate = useNavigate()

    return(
      <>
        <Navbar bg="dark" data-bs-theme="dark" > 
        <Container>
          <Navbar.Brand >ShoeShop</Navbar.Brand>
          <Nav className="me-auto" >
            <Nav.Link onClick={()=>{ navigate('/home')}}>Home</Nav.Link>
            <Nav.Link onClick={()=>{ navigate('/recipe')}}>Recipe</Nav.Link>
            <Nav.Link onClick={()=>{ navigate('/diet')}}>Diet</Nav.Link>
            <Nav.Link onClick={()=>{ navigate('/diary')}}>Diary</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
      </>
    )
}

export default NavbarModule