'use strict';

describe('Controller Tests', function() {

    describe('Freecom_accreditation_ieps Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_accreditation_ieps, MockC_tar, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_accreditation_ieps = jasmine.createSpy('MockFreecom_accreditation_ieps');
            MockC_tar = jasmine.createSpy('MockC_tar');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_accreditation_ieps': MockFreecom_accreditation_ieps,
                'C_tar': MockC_tar,
                'Free_cfdi': MockFree_cfdi
            };
            createController = function() {
                $injector.get('$controller')("Freecom_accreditation_iepsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_accreditation_iepsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
