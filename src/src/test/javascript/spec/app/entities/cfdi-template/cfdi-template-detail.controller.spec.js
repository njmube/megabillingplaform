'use strict';

describe('Controller Tests', function() {

    describe('Cfdi_template Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCfdi_template;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCfdi_template = jasmine.createSpy('MockCfdi_template');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cfdi_template': MockCfdi_template
            };
            createController = function() {
                $injector.get('$controller')("Cfdi_templateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:cfdi_templateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
