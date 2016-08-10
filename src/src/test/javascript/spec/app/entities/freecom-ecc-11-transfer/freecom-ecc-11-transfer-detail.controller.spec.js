'use strict';

describe('Controller Tests', function() {

    describe('Freecom_ecc11_transfer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_ecc11_transfer, MockFreecom_ecc11_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_ecc11_transfer = jasmine.createSpy('MockFreecom_ecc11_transfer');
            MockFreecom_ecc11_concept = jasmine.createSpy('MockFreecom_ecc11_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_ecc11_transfer': MockFreecom_ecc11_transfer,
                'Freecom_ecc11_concept': MockFreecom_ecc11_concept
            };
            createController = function() {
                $injector.get('$controller')("Freecom_ecc11_transferDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_ecc11_transferUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
