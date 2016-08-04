'use strict';

describe('Controller Tests', function() {

    describe('Freecom_paybill_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_paybill_concept, MockFreecom_storeroom_paybill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_paybill_concept = jasmine.createSpy('MockFreecom_paybill_concept');
            MockFreecom_storeroom_paybill = jasmine.createSpy('MockFreecom_storeroom_paybill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_paybill_concept': MockFreecom_paybill_concept,
                'Freecom_storeroom_paybill': MockFreecom_storeroom_paybill
            };
            createController = function() {
                $injector.get('$controller')("Freecom_paybill_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_paybill_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
