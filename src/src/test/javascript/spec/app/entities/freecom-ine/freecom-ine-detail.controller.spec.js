'use strict';

describe('Controller Tests', function() {

    describe('Freecom_ine Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_ine, MockCommittee, MockProcess_type, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_ine = jasmine.createSpy('MockFreecom_ine');
            MockCommittee = jasmine.createSpy('MockCommittee');
            MockProcess_type = jasmine.createSpy('MockProcess_type');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_ine': MockFreecom_ine,
                'Committee': MockCommittee,
                'Process_type': MockProcess_type,
                'Free_cfdi': MockFree_cfdi
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
