package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
@NamedEntityGraph(name = "comment-book-entity-graph",
        attributeNodes = {@NamedAttributeNode("book")})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nick_name", nullable = false, unique = false)
    private String nickname;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    private Book book;

    @Override
    public String toString() {
        return "Comment(id=" + id + ", nickname=" + nickname + ", text=" + text + ", book(id=" + book.getId() + ", title=" + book.getTitle() + "))" ;
    }

}
