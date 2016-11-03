'use strict';

describe('Controller Tests', function() {

    describe('Com_taxregistration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_taxregistration, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_taxregistration = jasmine.createSpy('MockCom_taxregistration');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_taxregistration': MockCom_taxregistration,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_taxregistrationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_taxregistrationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
