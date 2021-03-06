'use strict';

describe('Controller Tests', function() {

    describe('Freecom_ine Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_ine, MockFree_cfdi, MockC_committee_type, MockC_process_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_ine = jasmine.createSpy('MockFreecom_ine');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockC_committee_type = jasmine.createSpy('MockC_committee_type');
            MockC_process_type = jasmine.createSpy('MockC_process_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_ine': MockFreecom_ine,
                'Free_cfdi': MockFree_cfdi,
                'C_committee_type': MockC_committee_type,
                'C_process_type': MockC_process_type
            };
            createController = function() {
                $injector.get('$controller')("Freecom_ineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_ineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
