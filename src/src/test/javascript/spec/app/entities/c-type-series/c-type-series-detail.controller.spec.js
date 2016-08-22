'use strict';

describe('Controller Tests', function() {

    describe('C_type_series Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_type_series;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_type_series = jasmine.createSpy('MockC_type_series');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_type_series': MockC_type_series
            };
            createController = function() {
                $injector.get('$controller')("C_type_seriesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_type_seriesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
