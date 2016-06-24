'use strict';

describe('Controller Tests', function() {

    describe('Cfdi_type_doc Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCfdi_type_doc;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCfdi_type_doc = jasmine.createSpy('MockCfdi_type_doc');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cfdi_type_doc': MockCfdi_type_doc
            };
            createController = function() {
                $injector.get('$controller')("Cfdi_type_docDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:cfdi_type_docUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
