(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_tfdDetailController', Com_tfdDetailController);

    Com_tfdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_tfd'];

    function Com_tfdDetailController($scope, $rootScope, $stateParams, entity, Com_tfd) {
        var vm = this;

        vm.com_tfd = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_tfdUpdate', function(event, result) {
            vm.com_tfd = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
