## 📚 학습내용
1. **viewBinding** 사용
2. **RecyclerView**를 사용
4. **람다식**을 사용하여 아이템 클릭 이벤트 구현
5. **Parcelize**를 사용하여 dummy data를 전달하고 받아오기 구현
7. **싱글톤**을 사용하여 데이터 관리
8. **DecimalFormat**을 사용하여 콤마 표시
9. **다이얼로그**, **알림** 기능 사용</br></br>

## 🛠️ 상세기능
### 1) MainActivity

<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/ab810b20-58a7-43ca-90e8-a9532314015d" width="200" height="400">
<br/><br/>-> RecyclerView를 사용하여 여러개의 리스트들이 보이도록 구현
<br/>-> Parcelize를 사용하여 dummy data를 전달하고 받아오기 구현
<br/>-> DecimalFormat을 사용하여 1000단위로 콤마표시
<br/>-> DividerItemDecoration를 사용하여 구분선 표시
<br/>-> maxLines과 ellipsize="end를 사용하여 상품이름이 2줄이 넘어가면 ...처리
<br/>-> cardView를 사용하여 이미지모서리 라운드 처리
<br/><br/><br/><br/>



<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/4c943a69-6bab-4a16-a609-4e9a8db2abc5" width="200" height="400">
<br/><br/>-> 뒤로가기 버튼 클릭시 다이얼로그 띄우고, 확인을 누를시 앱 종료
<br/>-> onBackPressedDispatcher 사용
<br/><br/><br/><br/>



<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/6756ca79-7625-462e-8c49-2f6d018ca67a" width="200" height="400">
<br/><br/>-> 알림 아이콘 누르면 Notification 생성
<br/><br/><br/><br/>


<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/6c521f8a-87b0-43f4-9775-7e9de31d4ff6" width="200" height="400">
<br/><br/>-> 플로팅 버튼을 추가하여 클릭시 스크롤 최상단이동 구현
<br/>-> 스크롤을 아래로 내릴때 나타나며, 최상단일땐 사라짐
<br/>-> 나타나고 사라질때 fade효과 추가
<br/>-> 플로팅 버튼 클릭시 아이콘 색상 주황색으로 변경
<br/><br/><br/><br/>


<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/d11adfcc-62b2-41f4-9da7-a5ab8bfe19a7" width="200" height="400">
<br/><br/>-> 상품 2초간 클릭했을때 삭제여부를 묻는 다이얼로그 띄움
<br/>-> 확인 선택시 removeAt을 사용해서 클릭한 아이템 삭제후, notifyDataSetChanged 사용하여 리스트 업데이트
<br/><br/><br/><br/>



### 2) DetailActivtiy

<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/b1fdbbfe-dda6-41c5-bdf5-bdc90474e38c" width="200" height="400">
<br/><br/>-> 람다식을 사용하여 아이템 클릭 이벤트 구현
<br/>-> MainActivity에서 전달한 데이터를 받아와서 레이아웃에 뿌려줌
<br/>-> 하단 가격표시 레이아웃을 제외하고 전체화면은 스크롤되도록 구현
<br/><br/><br/><br/>



<img src="https://github.com/wndnjs00/CarrotMarket/assets/89961868/849d850e-88fd-42b5-9fd5-2ab1e007b0b1" width="200" height="400">
<br/><br/>-> 상품 상세 화면에서 좋아요 선택시 좋아요 아이콘 변경 및 Snackbar 메세지 표시
<br/>-> 메인 화면으로 돌아오면 해당 상품에 좋아요 표시 및 좋아요 카운트 +1
<br/>-> 상세 화면에서 좋아요 해제시 이전 상태로 되돌림
<br/>-> 클릭한 postion값 전달한뒤, registerForActivityResult를 사용하여 구현
<br/><br/><br/><br/>

