# SuperFunBoardGame

## 프로젝트 이해

### 지도 로드 기능

- 프로그램이 실행되고, map을 선택할 수 있다.
- 주어진 default.map과 another.map을 각각 1,2번으로 설정하여 입력해서 맵을 로드할 수 있다.

### 플레이 기능

- 프로그램을 실행하면 플레이어의 수를 2~4명 사이로 설정할 수 있는 입력을 받는다.
- 각 플레이어는 map에 해당하는 좌표에 1,2,3,4와 같이 표현된다.
    - 플레이어가 겹쳐있는 경우는 번호가 큰 플레이어가 위에 뜨도록 한다.
- 플레이어들의 점수, 카드를 함께 보여준다.
- 게임이 종료되면 각 플레이어의 점수를 보여주고 승자를 보여준다.
- 각 플레이어의 턴에 1을 입력하면 stay, 2를 입력하면 move를 진행하게 된다.
- move를 하는 경우 랜덤하게 주사위를 굴려 1~6 사이의 숫자가 주어지게 된다.
- player는 주사위 숫자와 카드의 개수를 고려해서 콘솔에 뜨는 입력가능한 command 개수를 맞춰서 대문자로 command를 입력해야한다.
- 다리를 건너는 경우는 문서의 예시에 있는 것과 같이 이정표가 있는 대문자 B에서만 건널 수 있게 하였다.
- 다리를 건너면 다리 카드로 패널티가 부여되고, 패널티가 있으므로 출발지점이 B아 아니더라도 다리를 건널 수 있도록 하였다.

### 유저 인터페이스 관련 사항

- 콘솔 인터페이스를 통해 게임이 동작하도록 구현하였다.

# 요구 정의 및 분석

요구 정의 및 분석을 시행하기 위해서 Use Case Diagram, Use Case 명세, Domain Model, System Sequence Diagram을 사용하였다.

## Use Case Diagram

<img width="909" alt="Screen_Shot_2022-06-08_at_10 11 19_PM" src="https://user-images.githubusercontent.com/89574881/172787903-34981ebc-9e60-434f-98ec-ccd661c3a2a3.png">

프로그램을 실행하면 map과 player 인원을 설정할 수 있는데 이를 행하는 Actor는 게임을 진행하는 Player와는 다르므로 Starter라는 이름으로 따로 두었다.

Starter는 Start Game이라는 Use Case를 진행하는데 Start Game은 Initialize Map와 Initialize Players라는 Use Case로 이루어져 있다.

Initialize Map은 Map data Actor와 연관이 있으므로 연결시켜주었다.

게임을 진행하는 Player는 2~4명이 있다고 표현해줬고, turn은 stay, move 중 하나를 선택하게 되므로 이를 Use Case로 나눠주었다.

또한 move는 주사위를 굴리고, 주사위 수만큼 command를 입력하고, command대로 움직이는 절차가 포함되므로 이를 Use Case로 표현을 해주었다.

## Use Case 명세

Use Case Diagram으로 부족한 표현을 Use Case 명세를 통해 더 자세하게 기술했다.

## Use Case: 게임 시작

- 관련 Actor
    - Starter
    - Map data
- 요약
    
    게임이 시작되고 player의 숫자, map을 결정하는 과정
    
    player는 2~4명으로 결정해야하고, map은 1~2번 중 선택해야한다.
    
- 종료 조건
    
    게임을 진행하는 player의 인원과 map 번호를 입력
    

### 기본 흐름

1. player 숫자 입력
2. map 번호 입력

## Use Case: Stay

- 관련 Actor
    - Player
- 요약
    
    player가 자신의 turn일 때 stay로 결정했을 때의 과정
    
    bridge card를 하나 낮추고 움직이지 않고 turn을 넘김
    
- 선행 조건
    
    자신의 turn일 때 stay와 move 중 stay 선택
    

### 기본 흐름

1. 해당 player가 bridge card가 있을 경우 하나를 반납하도록 함

## Use Case: Move

- 관련 Actor
    - Player
- 요약
    
    player가 자신의 turn일 때 move로 결정했을 때의 과정.
    
    주사위 숫자에 맞게 command를 입력하고 command에 따라 player가 움직인다. 진행할 수 없는 command를 입력하면 다시 입력을 받도록 한다.
    
- 선행 조건
    
    자신의 turn일 때 stay와 move 중 move 선택
    

### 기본 흐름

1. 주사위 굴리기
2. 주사위 숫자 - bridge card만큼 command 입력
3. 올바른 command일 경우 player 위치 바꿔주기

## Domain Model

<img width="884" alt="Screen_Shot_2022-06-08_at_11 24 37_PM" src="https://user-images.githubusercontent.com/89574881/172787912-4e420ef2-39e4-4b90-add3-1e45d243d926.png">

구조를 파악하기 쉽도록 Domain Model를 간단하게 작성했다. 

Rule이 2~4개의 Player와 1개의 map, 1개의 dice를 가지고 게임이 진행되는 구조이다.

## System Sequence Diagram

대략적인 흐름을 파악하기 위해 system sequence diagram을 그려보았다.

<img width="660" alt="Screen_Shot_2022-06-09_at_1 35 54_AM" src="https://user-images.githubusercontent.com/89574881/172787920-1dcc06b7-9a3f-46e8-bb20-ac38bc7e670f.png">

현재 Use Case에서 핵심적인 게임 생성과 turn의 과정이다.

게임을 생성하기 위해서 starter가 system에게 player의 수와 map의 번호를 파라미터로 하는 게임을 실행하는 명령을 하면 게임이 실행된다.

자기의 turn인 player는 move와 stay중 선택을 하고, 

move일 경우 주사위를 굴리고, command를 입력해서 player의 좌표를 옮기게 된다.

stay를 할 경우 카드를 반납하고 turn을 종료한다.

# 설계

설계를 위해서 Design Class Diagram, Sequence Diagram을 사용했다.

## Design Class Diagram

Design Class Diagram은 MVC를 적용을 염두하고 설계를 진행했고, test 또한 고려하였다.
![Untitled](https://user-images.githubusercontent.com/89574881/172788101-1823092e-e5b0-4029-a433-1fce7c1b6c7d.png)

MVC에 따라 크게 3가지 + test 총 4종류로 클래스를 나누었다.

View

- GUIView
- CLIView

Controller

- GUIController
- CLIController

Model

- RuleModel
- PlayerModel
- MapModel
- DiceModel
- MapCellModel
- PositionModel

Test

- DiceModelTest
- PlayerModelTest
- MapModelTest
- RuleModelTest

### View

view의 경우 model과의 의존성을 없애서 view가 바뀌더라도 model에 변화를 주지 않아도 되도록 설계하였다. 또한 CLI, GUI로 나눴다.

GUI 경우는 아직 구현이 완성되지 않아서 main에 연결시키지 않았다.

### Controller

Controller는 CLIController, GUIController 두가지로 나눴고, 둘 다 각각 CLI, GUI 환경에서 view에서의 사용자의 action을 인지하고 model에 전달하는 역할을 하도록 했다. 

### Model

PositionModel의 경우 맵 위의 좌표를 쉽게 표현하기 위해서 만들었고, MapCellModel 또한 map의 한 칸 한 칸의 정보를 쉽게 표현하기 위해서 만들었다.

로직이 담긴 model은 RuleModel, PlayerModel, MapModel, DiceModel이다.

DiceModel은 주사위를 굴려 1과 6 사이의 숫자를 넘겨주는 역할을 하는 클래스이다.

PlayerModel은 Player 각각의 정보(score, position, bridgeCardm isEnd)를 포함하고, 움직이는 것에 대한 로직들이 담겨있다.

MapModel은 정해진 Map을 읽고, MapCellModel(cellState, preDirection, nextDirection으로 구성)로 구성된 맵을 표현하는 2차원 배열에 저장한다.

RuleModel은 player와 map을 사용해서 게임의 전체적인 흐름을 관장하는 로직을 가진 모델이다. player들을 관리하고, map또한 관리하며 점수를 올리거나, 게임이 끝났다는 것을 알려준다.

Model은 Controller와 View를 알 지 못하고, 내부에서 로직을 진행해서 View와 Controller에 의존적이지 않도록 설계하였다.

따라서 View와 Controller에 변화가 생기더라도 Model Class들을 바꾸지 않도록 유도하였다.

### Test

테스트는 모든 것을 하지 않고, 로직이 들어있는 Model을 대상으로 진행했다.

수업에서 배운 JUnit을 사용해서 내가 원하는 값이 나왔는지, 원하는 오류가 나왔는지 비교를 해보며 unit test를 진행하도록 설계했다.

## Sequence Diagram

<img width="1180" alt="Screen_Shot_2022-06-09_at_1 41 54_PM" src="https://user-images.githubusercontent.com/89574881/172787928-52a96af7-82ee-4250-a44a-b46ca5c6d411.png">

<img width="1181" alt="Screen_Shot_2022-06-09_at_1 42 11_PM" src="https://user-images.githubusercontent.com/89574881/172787937-b895f78f-e5ec-4eb5-a332-7b9fea6fa7e8.png">

프로그램의 순서를 명확하게 파악하기 위해서 sequence diagram을 작성하였다.

main에서 GUIcontroller 또는 CLIController를 선택하고, Controller가 View클래스와 Model클래스를 생성한다.

View는 Model과 독립적으로 Contoller의 요청에 따라 화면을 출력하고 입력을 받는다.

게임 세팅이 끝나면 runGame이라는 메소드를 실행하는데 이는 반복적으로 시행되며 player들의 turn을 이동시켜주면서 로직을 진행한다.


