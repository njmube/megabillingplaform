'use strict';

describe('Controller Tests', function() {

    describe('Com_taxlegends Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_taxlegends, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_taxlegends = jasmine.createSpy('MockCom_taxlegends');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_taxlegends': MockCom_taxlegends,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_taxlegendsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_taxlegendsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
