import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewsWriteComponent} from './news-write.component';

describe('NewsWriteComponent', () => {
  let component: NewsWriteComponent;
  let fixture: ComponentFixture<NewsWriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsWriteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsWriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
