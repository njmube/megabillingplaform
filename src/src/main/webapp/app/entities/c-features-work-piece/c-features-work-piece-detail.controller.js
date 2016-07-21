(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_features_work_pieceDetailController', C_features_work_pieceDetailController);

    C_features_work_pieceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_features_work_piece'];

    function C_features_work_pieceDetailController($scope, $rootScope, $stateParams, entity, C_features_work_piece) {
        var vm = this;
        vm.c_features_work_piece = entity;
        vm.load = function (id) {
            C_features_work_piece.get({id: id}, function(result) {
                vm.c_features_work_piece = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_features_work_pieceUpdate', function(event, result) {
            vm.c_features_work_piece = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
