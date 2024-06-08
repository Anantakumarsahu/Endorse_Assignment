# Endorse_Assignment
It is a skill endorsement-based application

All the files are inside the master branch

I have also attached all the queries that I have used in my assignment in the All_SqlQuery_IUsed_Assessment file And I have also attached sample output images in it too.

The Tech stack that I have used-  Java, SpringBoot, Spring Data JPA, REST API, Mysql Db

So Overall there are 2 API:-

1) Post EndorsementAPI - (http://localhost:8083/api/endorse)
Request - {
    "revieweeUserId": 1,
    "reviewerUserId": 3,
    "skillId": 4,
    "score": 9
}
Response - {
    "reason": "Both are current coworkers with different skills but the same department. Reviewer has more experience.",
    "adjustedScore": 7.0
}

2) GET Endorse API - (http://localhost:8083/api/endorsements/{user_id})
Request - {}
Response - {
    "Java": [
        "User2(Jane Smith) - Score Endorsed: 7 ( Adjusted Score:2.5 with reason: Both are not current coworkers with different skills and different departments. Reviewer has less experience.)",
        "User3(Alice Johnson) - Score Endorsed: 4 ( Adjusted Score:10.0 with reason: Both are current coworkers with the same skills and department. Reviewer has more experience.)",
        "User3(Alice Johnson) - Score Endorsed: 7 ( Adjusted Score:7.0 with reason: Both are current coworkers with different skills but the same department. Reviewer has more experience.)",
        "User3(Alice Johnson) - Score Endorsed: 9 ( Adjusted Score:7.0 with reason: Both are current coworkers with different skills but the same department. Reviewer has more experience.)"
    ],
    "Sales Strategy": [
        "User3(Alice Johnson) - Score Endorsed: 6 ( Adjusted Score:4.0 with reason: Both are current coworkers with different skills and different departments. Reviewer has more experience.)"
    ],
    "Spring Boot": [
        "User3(Alice Johnson) - Score Endorsed: 7 ( Adjusted Score:7.0 with reason: Both are current coworkers with different skills but the same department. Reviewer has more experience.)"
    ]
}

So this are the overall 2 APi working.
