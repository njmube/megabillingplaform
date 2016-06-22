'use strict';

describe('Controller Tests', function() {

    describe('Cfdi_types Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCfdi_types;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCfdi_types = jasmine.createSpy('MockCfdi_types');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cfdi_types': MockCfdi_types
            };
            createController = function() {
                $injector.get('$controller')("Cfdi_typesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:cfdi_typesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
