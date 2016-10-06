(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Ring_packDetailController', Ring_packDetailController);

    Ring_packDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Ring_pack'];

    function Ring_packDetailController($scope, $rootScope, $stateParams, entity, Ring_pack) {
        var vm = this;

        vm.ring_pack = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:ring_packUpdate', function(event, result) {
            vm.ring_pack = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
