soa-conference-system
=====================

Conference System in Service Oriented Architecture

Usługi warstwy "zewnetrznej" (w nawiasie sciezka dla Androida):

AddConferenceService (.../conference/add)

- Input - obiekt klasy Conference, pole id jest niewazne, jest uzupelniane przy dodawaniu do bazy
- Output - obiekt klasy Conference z dobrze uzupelnionym polem id lub kodem bledu w polu id, wtedy inne pola niewazne, kody: -1 - nazwa zajeta, -2 - inne niepowodzenie

AddConferenceToUserFavsService (.../conference/addtouserfav)

- Input - id dodawanej konferencji z kluczem "conferenceid" oraz id uzytkownika z kluczem "userid" (dla Androida obiekt klasy UserAndConferenceIDs)
- Output - obiekt List\<Conference\> zawierajacy liste ulubionych konferencji uzytkownika

GetUserFavsService (.../conference/getuserfav)

- Input - obiekt klasy User z dobrym id - po zalogowaniu
- Output - obiekt List\<Conference\> zawierajacy ulubione konferencje uzytkownika lub null gdy takich nie ma

GetAllConferencesService (.../conference/getall)

- Input - nic, wywolanie jak HTTP GET
- Output - obiekt List\<Conference\> zawierajacy liste wszystkich konferencji lub null gdy takich nie ma

RegisterUserService (.../user/register)

- Input - obiekt klasy User z uzupelnionymi polami, pole id nie ma znaczenia, jest uzupelniane przy dodawaniu do bazy
- Output - obiekt klasy User z uzupelnionym dobrym id lub kodem bledu w polu id, reszta pol nie ma znaczenia, kody bledu: -1 - nick zajety, -2 inne niepowodzenie

LogInUserService (.../user/login)

- Input - obiekt klasy User z uzupelnionymi nickiem i haslem, reszta pol nie ma znaczenia
- Output - obiekt klasy User z uzupelnionymi polami lub kod bledu w miejscu id, wtedy reszta pol nie ma znaczenia, kody bledu: -1 - zly nick, -2 - zle haslo
