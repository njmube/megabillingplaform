'use strict';

describe('Controller Tests', function() {

    describe('Com_storeroom_paybill Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_storeroom_paybill, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_storeroom_paybill = jasmine.createSpy('MockCom_storeroom_paybill');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_storeroom_paybill': MockCom_storeroom_paybill,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_storeroom_paybillDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_storeroom_paybillUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
