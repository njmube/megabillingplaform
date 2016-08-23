(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tfdDetailController', Freecom_tfdDetailController);

    Freecom_tfdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_tfd'];

    function Freecom_tfdDetailController($scope, $rootScope, $stateParams, entity, Freecom_tfd) {
        var vm = this;
        vm.freecom_tfd = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_tfdUpdate', function(event, result) {
            vm.freecom_tfd = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
