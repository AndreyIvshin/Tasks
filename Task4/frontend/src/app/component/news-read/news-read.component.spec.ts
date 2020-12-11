import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewsReadComponent} from './news-read.component';

describe('NewsReadComponent', () => {
  let component: NewsReadComponent;
  let fixture: ComponentFixture<NewsReadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsReadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsReadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
