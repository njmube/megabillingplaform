(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_requestDetailController', Taxpayer_requestDetailController);

    Taxpayer_requestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_request'];

    function Taxpayer_requestDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_request) {
        var vm = this;

        vm.taxpayer_request = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_requestUpdate', function(event, result) {
            vm.taxpayer_request = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
