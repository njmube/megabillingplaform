(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_retentionsDetailController', Freecom_local_retentionsDetailController);

    Freecom_local_retentionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_local_retentions', 'Freecom_local_taxes'];

    function Freecom_local_retentionsDetailController($scope, $rootScope, $stateParams, entity, Freecom_local_retentions, Freecom_local_taxes) {
        var vm = this;

        vm.freecom_local_retentions = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_local_retentionsUpdate', function(event, result) {
            vm.freecom_local_retentions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
