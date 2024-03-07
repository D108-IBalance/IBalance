/* eslint-disable */

// 외부 모듈
import {Container,  Row, Col} from 'react-bootstrap';
import { useState } from 'react';

// 내부 모듈
import classes from './Profile.module.css';

const Profile = ()=>{
    // 이후 프로필 리스트 추가할 변수 ajax 통신 필요
    let profileList = ['zz','df','dd'];
    // 길이 정보를 통해 레이아웃( 4개 이상일 시 가운데 정렬 풂) 및 프로필 추가 버튼 여부 결정할 것
    let length = profileList.length;
    let justify = classes.formCenter; // default값은 가운데 정렬
    if( length < 6 ){ // 최대 프로필 수 6개이기에 만약 추가될 사항이 있으면 숫자 변경 가능
        profileList.push(null); // 객체 형식 맞추어서 이후 프로필 데이터 형식이 갖추어 지면 그에 맞게 추가
    }
    if ( length >= 3 ){ // 길이가 3개 이상일 시 ==> 밑에 레이아웃이 추가 됨, 이 경우 justfy-content : start로 값 변경
        justify = classes.formStart;
    }
    return(
        <main className={ classes.container }>
            <article className={ classes.profileWrap }>
                <div className={ classes.logo }/>
                    <Container className={ `${classes.profileForm} ${justify}` }>
                        {
                            // 유저의 프로필 갯수만큼 반복
                            profileList.map((content)=>{
                                return(
                                    // 6개 미만일 시 프로필 추가버튼을 렌더해야함 즉, 마지막에 넣은 null값이 프로필 추가 버튼
                                    content === null ? (
                                        <div className={classes.profile}>
                                            <div className={ classes.profilePlus }></div>
                                        </div>
                                    ):(
                                    // 유저 프로필 정보
                                    <div className={classes.profile}>
                                        <div className={ classes.profileIcon }></div>
                                        <p className={ classes.profileName }>{ content }</p>
                                    </div> 
                                    )   
                                )
                            })
                        }
                    </Container>
            </article>
        </main>
    )
}

export default Profile