package com.patrickontheweb.fit.repository.day;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patrickontheweb.fit.model.day.Day;
import com.patrickontheweb.fit.model.user.User;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

	Day findByUser(User user);
}
