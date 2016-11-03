'use strict';

describe('Controller Tests', function() {

    describe('Com_ecc_11 Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_ecc_11, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_ecc_11 = jasmine.createSpy('MockCom_ecc_11');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_ecc_11': MockCom_ecc_11,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_ecc_11DetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_ecc_11Update';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
