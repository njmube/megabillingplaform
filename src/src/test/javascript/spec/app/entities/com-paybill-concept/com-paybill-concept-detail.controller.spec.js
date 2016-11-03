'use strict';

describe('Controller Tests', function() {

    describe('Com_paybill_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_paybill_concept, MockCom_storeroom_paybill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_paybill_concept = jasmine.createSpy('MockCom_paybill_concept');
            MockCom_storeroom_paybill = jasmine.createSpy('MockCom_storeroom_paybill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_paybill_concept': MockCom_paybill_concept,
                'Com_storeroom_paybill': MockCom_storeroom_paybill
            };
            createController = function() {
                $injector.get('$controller')("Com_paybill_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_paybill_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
