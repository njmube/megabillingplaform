(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Features_work_pieceDetailController', Features_work_pieceDetailController);

    Features_work_pieceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Features_work_piece'];

    function Features_work_pieceDetailController($scope, $rootScope, $stateParams, entity, Features_work_piece) {
        var vm = this;
        vm.features_work_piece = entity;
        vm.load = function (id) {
            Features_work_piece.get({id: id}, function(result) {
                vm.features_work_piece = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:features_work_pieceUpdate', function(event, result) {
            vm.features_work_piece = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
