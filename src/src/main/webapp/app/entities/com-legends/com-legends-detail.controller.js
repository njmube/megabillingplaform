(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_legendsDetailController', Com_legendsDetailController);

    Com_legendsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_legends', 'Com_taxlegends'];

    function Com_legendsDetailController($scope, $rootScope, $stateParams, entity, Com_legends, Com_taxlegends) {
        var vm = this;
        vm.com_legends = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_legendsUpdate', function(event, result) {
            vm.com_legends = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
