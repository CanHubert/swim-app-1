import { TestBed } from '@angular/core/testing';

import { ProfileFacadeService } from './profile-facade.service';

describe('ProfileFacadeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProfileFacadeService = TestBed.get(ProfileFacadeService);
    expect(service).toBeTruthy();
  });
});
