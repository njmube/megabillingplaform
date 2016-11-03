'use strict';

describe('Controller Tests', function() {

    describe('Com_destruction_certificate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_destruction_certificate, MockCfdi, MockC_type_series;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_destruction_certificate = jasmine.createSpy('MockCom_destruction_certificate');
            MockCfdi = jasmine.createSpy('MockCfdi');
            MockC_type_series = jasmine.createSpy('MockC_type_series');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_destruction_certificate': MockCom_destruction_certificate,
                'Cfdi': MockCfdi,
                'C_type_series': MockC_type_series
            };
            createController = function() {
                $injector.get('$controller')("Com_destruction_certificateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_destruction_certificateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
