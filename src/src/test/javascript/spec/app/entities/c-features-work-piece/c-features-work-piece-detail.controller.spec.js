'use strict';

describe('Controller Tests', function() {

    describe('C_features_work_piece Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_features_work_piece;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_features_work_piece = jasmine.createSpy('MockC_features_work_piece');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_features_work_piece': MockC_features_work_piece
            };
            createController = function() {
                $injector.get('$controller')("C_features_work_pieceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_features_work_pieceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
